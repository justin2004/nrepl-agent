build:
	docker run --rm -it -v nrepl_agent_mvn_cache:/root/.m2 -v `pwd`:/mnt maven bash -c 'cd /mnt ; mvn clean compile assembly:single'
	echo now, find the jar at:
	ls -la target/*jar
