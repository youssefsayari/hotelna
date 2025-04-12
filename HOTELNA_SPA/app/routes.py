# app/routes.py
from flask import Blueprint, request, jsonify
from .models import Spa
from . import db

spa_bp = Blueprint('spa_bp', __name__)

@spa_bp.route('/getAllSpas', methods=['GET'])
def get_spas():
    spas = Spa.query.all()
    return jsonify([spa.to_dict() for spa in spas])

@spa_bp.route('/getSpaById/<int:id>', methods=['GET'])
def get_spa(id):
    spa = Spa.query.get_or_404(id)
    return jsonify(spa.to_dict())

@spa_bp.route('/addSpa', methods=['POST'])
def create_spa():
    data = request.get_json()
    if not data or not data.get('name'):
        return jsonify({'error': 'Missing required fields'}), 400

    new_spa = Spa(
        name=data['name'],
        description=data.get('description', ''),
        price=data.get('price', 0.0),
        available=data.get('available', True)
    )
    db.session.add(new_spa)
    db.session.commit()
    return jsonify(new_spa.to_dict()), 201

@spa_bp.route('/updateSpa/<int:id>', methods=['PUT'])
def update_spa(id):
    spa = Spa.query.get_or_404(id)
    data = request.get_json()

    spa.name = data.get('name', spa.name)
    spa.description = data.get('description', spa.description)
    spa.price = data.get('price', spa.price)
    spa.available = data.get('available', spa.available)

    db.session.commit()
    return jsonify(spa.to_dict())

@spa_bp.route('/deleteSpa/<int:id>', methods=['DELETE'])
def delete_spa(id):
    spa = Spa.query.get_or_404(id)
    db.session.delete(spa)
    db.session.commit()
    return jsonify({'message': 'Spa deleted'})