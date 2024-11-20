const glucoService = require('../services/glucoService');

class GlucoController {
  // Mengelola klasifikasi 
  static classifyRisk(req, res) {
    const { bloodSugar, bmi, age } = req.body;

    if (!bloodSugar || !bmi || !age) {
      return res.status(400).json({ error: 'All fields (bloodSugar, bmi, age) are required!' });
    }

    const classification = glucoService.classifyRisk(bloodSugar, bmi, age);
    res.status(200).json({ classification });
  }

  // Mengambil daftar web
  static getResources(req, res) {
    const resources = glucoService.getHealthResources();
    res.status(200).json({ resources });
  }
}

module.exports = GlucoController;
