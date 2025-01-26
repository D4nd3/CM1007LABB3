"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const sharp_1 = __importDefault(require("sharp"));
const path_1 = __importDefault(require("path"));
const router = (0, express_1.Router)();
const addTextHandler = async (req, res) => {
    const { filename, text } = req.body;
    if (!filename || !text) {
        res.status(400).send('Filnamn och text är obligatoriska.');
        return;
    }
    const inputPath = path_1.default.join(__dirname, '../../uploads/', filename);
    const newFilename = `edited-${filename}`;
    const outputPath = path_1.default.join(__dirname, '../../uploads/', newFilename);
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
        await (0, sharp_1.default)(inputPath)
            .composite([
            {
                input: Buffer.from(svgImage),
                gravity: 'south',
            },
        ])
            .toFile(outputPath);
        res.status(200).send({ message: 'Text tillagd!', filename: newFilename });
    }
    catch (err) {
        console.error('Fel vid redigering av bilden:', err);
        res.status(500).send('Fel vid redigering av bilden.');
    }
};
router.post('/add-text', addTextHandler);
exports.default = router;
