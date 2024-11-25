const express = require('express');
const app = express();
const glucoRoutes = require('./routes/glucoRoutes');

// Middleware
app.use(express.json());
app.use('/api/v1', glucoRoutes);

// Jalankan server
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});
