sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping

pip install httpie

mkdir -p temp && cd temp &&
if [ ! -f siege-4.0.4.tar.gz ]; then
  wget https://download.joedog.org/siege/siege-4.0.4.tar.gz
fi &&
if [ ! -d siege-4.0.4 ]; then
  tar -xvf siege-4.0.4.tar.gz
fi &&
cd siege-4.0.4 &&
./configure --prefix=$HOME/siege > /dev/null 2>&1 &&
sudo make install > /dev/null 2>&1 &&
echo 'export PATH=$PATH:$HOME/siege/bin' >> ~/.bashrc &&
cd /workspace/lab-shop-pub-sub &&

cd kafka
docker-compose up
