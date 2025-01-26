"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const multer_1 = __importDefault(require("multer"));
const path_1 = __importDefault(require("path"));
const fs_1 = __importDefault(require("fs"));
const router = (0, express_1.Router)();
const storage = multer_1.default.diskStorage({
    destination: (req, file, cb) => {
        const uploadPath = path_1.default.join(__dirname, '../../uploads/');
        if (!fs_1.default.existsSync(uploadPath)) {
            console.log(`Skapar mappen: ${uploadPath}`);
            fs_1.default.mkdirSync(uploadPath, { recursive: true });
        }
        cb(null, uploadPath);
    },
    filename: (req, file, cb) => {
        cb(null, `${Date.now()}-${file.originalname}`);
    },
});
const upload = (0, multer_1.default)({ storage });
router.post('/', upload.single('image'), (req, res) => {
    const file = req.file;
    if (!file || !file.filename) {
        res.status(400).send('Ingen bild uppladdad.');
    }
    res.status(200).send({
        message: 'Bild uppladdad!',
        //@ts-ignore
        filename: req.file.filename,
    });
});
router.get('/all', (_req, res) => {
    const uploadPath = path_1.default.join(__dirname, '../../uploads/');
    if (!fs_1.default.existsSync(uploadPath)) {
        res.status(404).send({ message: 'Mappen uploads/ hittades inte.' });
        return;
    }
    try {
        const files = fs_1.default.readdirSync(uploadPath);
        res.status(200).send({ files });
    }
    catch (error) {
        console.error('Fel vid läsning av filer:', error);
        res.status(500).send({ message: 'Fel vid läsning av filer.' });
    }
});
router.get('/:filename', (req, res) => {
    const filename = decodeURIComponent(req.params.filename);
    const filepath = path_1.default.join(__dirname, '../../uploads/', filename);
    if (!fs_1.default.existsSync(filepath)) {
        res.status(404).send({ message: `Filen ${filename} hittades inte.` });
        return;
    }
    res.sendFile(filepath);
});
exports.default = router;
