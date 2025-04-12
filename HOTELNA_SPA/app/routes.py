#routes.py
from flask import Blueprint, request, jsonify
from bson.objectid import ObjectId
from .models import Spa
from . import mongo

spa_bp = Blueprint('spa_bp', __name__)
spa_collection = mongo.db.spa  # MongoDB collection

@spa_bp.route('/getAllSpas', methods=['GET'])
def get_spas():
    spas = spa_collection.find()
    result = [Spa.from_mongo(spa).to_dict() for spa in spas]
    return jsonify(result)

@spa_bp.route('/getSpaById/<string:id>', methods=['GET'])
def get_spa(id):
    try:
        spa = spa_collection.find_one({'_id': ObjectId(id)})
        if not spa:
            return jsonify({'error': 'Spa not found'}), 404
        return jsonify(Spa.from_mongo(spa).to_dict())
    except:
        return jsonify({'error': 'Invalid ID'}), 400

@spa_bp.route('/addSpa', methods=['POST'])
def create_spa():
    data = request.get_json()
    if not data or not data.get('name'):
        return jsonify({'error': 'Missing required fields'}), 400

    spa = Spa(
        name=data['name'],
        description=data.get('description', ''),
        price=data.get('price', 0.0),
        available=data.get('available', True)
    )

    inserted = spa_collection.insert_one(spa.to_dict())
    spa.id = str(inserted.inserted_id)
    return jsonify(spa.to_dict()), 201

@spa_bp.route('/updateSpa/<string:id>', methods=['PUT'])
def update_spa(id):
    data = request.get_json()
    update_fields = {
        k: v for k, v in data.items()
        if k in ['name', 'description', 'price', 'available']
    }

    result = spa_collection.update_one(
        {'_id': ObjectId(id)},
        {'$set': update_fields}
    )

    if result.matched_count == 0:
        return jsonify({'error': 'Spa not found'}), 404

    updated_spa = spa_collection.find_one({'_id': ObjectId(id)})
    return jsonify(Spa.from_mongo(updated_spa).to_dict())

@spa_bp.route('/deleteSpa/<string:id>', methods=['DELETE'])
def delete_spa(id):
    result = spa_collection.delete_one({'_id': ObjectId(id)})
    if result.deleted_count == 0:
        return jsonify({'error': 'Spa not found'}), 404
    return jsonify({'message': 'Spa deleted'})
