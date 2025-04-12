#init.py
from flask import Flask
from flask_pymongo import PyMongo
from config import MONGO_URI

mongo = PyMongo()

def create_app():
    app = Flask(__name__)
    app.config["MONGO_URI"] = MONGO_URI

    mongo.init_app(app)

    from .routes import spa_bp
    app.register_blueprint(spa_bp, url_prefix="/spa")

    return app
