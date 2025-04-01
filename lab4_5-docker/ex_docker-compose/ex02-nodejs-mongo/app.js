const express = require('express');
const mongoose = require('mongoose');
const app = express();
const port = 3000;

// Cấu hình kết nối MongoDB
mongoose.connect('mongodb://mongo:27017/mydb', {
  useNewUrlParser: true,
  useUnifiedTopology: true
});

// Kiểm tra kết nối MongoDB
mongoose.connection.on('connected', () => {
  console.log('MongoDB connected successfully');
});

mongoose.connection.on('error', (err) => {
  console.log('MongoDB connection error:', err);
});

// Định nghĩa một model đơn giản
const Item = mongoose.model('Item', new mongoose.Schema({
  name: String
}));

// Tạo REST API đơn giản
app.get('/', (req, res) => {
  res.send('Hello, World!');
});

app.get('/items', async (req, res) => {
  const items = await Item.find();
  res.json(items);
});

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
