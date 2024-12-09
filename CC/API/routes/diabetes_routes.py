from flask import Blueprint, request, jsonify
from controllers.diabetes_controller import predict_diabetes

diabetes_bp = Blueprint('diabetes', __name__)

@diabetes_bp.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()
        result = predict_diabetes(data)
        return jsonify(result)
    except KeyError as e:
        return jsonify({"error": f"Missing feature: {str(e)}"}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500