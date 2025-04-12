#run.py
from app import create_app
from flask.cli import with_appcontext
import click
import py_eureka_client.eureka_client as eureka_client  # ✅ correct import

app = create_app()

# ✅ Correct usage of Eureka registration
eureka_client.init(
    eureka_server="http://localhost:8761/eureka",
    app_name="spa",
    instance_port=5000,
    instance_ip="127.0.0.1",
    region="default",
    data_center_name="MyOwn",
    health_check_url="http://127.0.0.1:5000/spa/health"
)


@app.cli.command("init-db")
@with_appcontext
def init_db():
    db.create_all()
    click.echo("Initialized the database.")

if __name__ == "__main__":
    app.run(debug=True, port=5000)
