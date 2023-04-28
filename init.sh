sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
wget https://download.joedog.org/siege/siege-4.0.4.tar.gz
tar -xvf siege-4.0.4.tar.gz
cd siege-4.0.4
./configure --prefix=$HOME/siege > /dev/null 2>&1
sudo make install > /dev/null 2>&1
export PATH=$PATH:$HOME/siege/bin
cd /workspace/lab-shop-monolith/
pip install httpie

cd kafka
docker-compose up
