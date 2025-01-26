"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
const upload_1 = __importDefault(require("./routes/upload"));
const edit_1 = __importDefault(require("./routes/edit"));
const app = (0, express_1.default)();
const PORT = 8085;
app.use(body_parser_1.default.json());
app.use('/uploads', express_1.default.static('uploads'));
app.use('/api/upload', upload_1.default);
app.use('/api/edit', edit_1.default);
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
