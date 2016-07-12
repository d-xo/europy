from flask import Flask, render_template
app = Flask(__name__)

@app.route("/")
def login():
    return render_template("login.html")

@app.route("/login_error")
def login_error():
    return render_template("login.html", error=True)

@app.route("/logged_in")
def logged_in():
    return render_template("logged_in.html")

if __name__ == "__main__":
    app.run()