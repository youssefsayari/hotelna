#init.py
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from config import SQLALCHEMY_DATABASE_URI, SQLALCHEMY_TRACK_MODIFICATIONS

# Global SQLAlchemy instance
db = SQLAlchemy()

def create_app():
    app = Flask(__name__)
    app.config['SQLALCHEMY_DATABASE_URI'] = SQLALCHEMY_DATABASE_URI
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = SQLALCHEMY_TRACK_MODIFICATIONS

    db.init_app(app)

    # Register your routes blueprint
    from .routes import spa_bp
    app.register_blueprint(spa_bp, url_prefix="/spa")

    return app
