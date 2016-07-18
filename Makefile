run:
	export FLASK_DEBUG=1 && python src/app/site.py

test:
	cd src/Graphwalker && mvn graphwalker:test