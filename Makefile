run:
	export FLASK_DEBUG=1 && python src/app/site.py

test:
	mvn test