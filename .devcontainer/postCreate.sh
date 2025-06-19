#!/bin/bash
set -e

echo "[INFO] Installing dependencies"

sudo apt-get update -y
sudo DEBIAN_FRONTEND=noninteractive apt-get install -y libfreetype6 libfontconfig1

echo "[INFO] Installation successful"

echo "[INFO] Compiling all Java source files..."

mkdir -p bin

# Compile all Java files from src to bin, preserving packages
javac -d bin $(find src -name "*.java")

echo "[INFO] Compilation complete."
echo "Run with: java -cp bin ascii_art.Shell <path_to_your_image>"
