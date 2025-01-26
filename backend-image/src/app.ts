import express from 'express';
import bodyParser from 'body-parser';
import uploadRoutes from './routes/upload';
import editRoutes from './routes/edit';

const app = express();
const PORT = 8085;

app.use(bodyParser.json());
app.use('/uploads', express.static('uploads'));

app.use('/api/upload', uploadRoutes);
app.use('/api/edit', editRoutes);

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});