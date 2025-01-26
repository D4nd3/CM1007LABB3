import { Router, RequestHandler } from 'express';
import sharp from 'sharp';
import path from 'path';

const router = Router();


interface AddTextRequestBody {
  filename: string;
  text: string;
}

const addTextHandler: RequestHandler<{}, any, AddTextRequestBody> = async (req, res) => {
  const { filename, text } = req.body;

  if (!filename || !text) {
    res.status(400).send('Filnamn och text är obligatoriska.');
    return;
  }

  const inputPath = path.join(__dirname, '../../uploads/', filename);
  const newFilename = `edited-${filename}`;
  const outputPath = path.join(__dirname, '../../uploads/', newFilename);

  try {
    const svgImage = `
      <svg width="500" height="80">
        <style>
          .overlay-text {
            fill: #000;
            font-size: 40px;
            font-weight: bold;
          }
        </style>
        <text x="50%" y="50%" text-anchor="middle" dominant-baseline="middle" class="overlay-text">
          ${text}
        </text>
      </svg>
    `;

    await sharp(inputPath)
      .composite([
        {
          input: Buffer.from(svgImage),
          gravity: 'south',
        },
      ])
      .toFile(outputPath);

    res.status(200).send({ message: 'Text tillagd!', filename: newFilename });
  } catch (err) {
    console.error('Fel vid redigering av bilden:', err);
    res.status(500).send('Fel vid redigering av bilden.');
  }
};

router.post<{}, any, AddTextRequestBody>('/add-text', addTextHandler);

export default router;