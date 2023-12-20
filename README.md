# BE-TC
docker build . -t tutorcenter

docker run -p 6379:6379 -d redis
docker run -p 9000:8080 tutorcenter 
