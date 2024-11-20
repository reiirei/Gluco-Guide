class GlucoService {
    // Fungsi untuk klasifikasi simple sesuai input user
    static classifyRisk(bloodSugar, bmi, age) {
      if (bloodSugar < 100 && bmi < 25 && age < 40) {
        return 'No Risk';
      } else if (bloodSugar < 140 && bmi < 30 && age < 50) {
        return 'Low Risk';
      } else if (bloodSugar < 200 || bmi < 35 || age < 60) {
        return 'At Risk';
      } else {
        return 'Diabetic';
      }
    }
  
    // Fungsi untuk menyediakan daftar web
    static getHealthResources() {
      return [
        { title: 'Healthy Eating', link: 'https://www.diabetes.org/nutrition' },
        { title: 'Exercise Tips', link: 'https://www.diabetes.org/fitness' },
        { title: 'Managing Diabetes', link: 'https://www.diabetes.org/management' },
      ];
    }
  }
  
  module.exports = GlucoService;
  