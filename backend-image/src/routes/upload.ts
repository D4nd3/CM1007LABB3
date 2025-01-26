import { Router, Request, Response } from 'express';
import multer from 'multer';
import path from 'path';
import fs from 'fs';

const router = Router();

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    const uploadPath = path.join(__dirname, '../../uploads/');

    if (!fs.existsSync(uploadPath)) {
      console.log(`Skapar mappen: ${uploadPath}`);
      fs.mkdirSync(uploadPath, { recursive: true });
    }

    cb(null, uploadPath);
  },
  filename: (req, file, cb) => {
    cb(null, `${Date.now()}-${file.originalname}`);
  },
});

const upload = multer({ storage });

router.post('/', upload.single('image'), (req: Request, res: Response) => {
  const file = req.file as Express.Multer.File | undefined;
  
  if (!file || !file.filename) {
    res.status(400).send('Ingen bild uppladdad.');
  }
  
  res.status(200).send({
    message: 'Bild uppladdad!',
    //@ts-ignore
    filename: req.file.filename,
  });
});

router.get('/all', (_req: Request, res: Response) => {
  const uploadPath = path.join(__dirname, '../../uploads/');

  if (!fs.existsSync(uploadPath)) {
    res.status(404).send({ message: 'Mappen uploads/ hittades inte.' });
    return;
  }

  try {
    const files = fs.readdirSync(uploadPath);
    res.status(200).send({ files });
  } catch (error) {
    console.error('Fel vid läsning av filer:', error);
    res.status(500).send({ message: 'Fel vid läsning av filer.' });
  }
});

router.get('/:filename', (req: Request, res: Response) => {
  const filename = decodeURIComponent(req.params.filename);
  const filepath = path.join(__dirname, '../../uploads/', filename);

  if (!fs.existsSync(filepath)) {
    res.status(404).send({ message: `Filen ${filename} hittades inte.` });
    return;
  }

  res.sendFile(filepath);
});

export default router;