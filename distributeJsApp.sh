#!/bin/bash

# Ensure script stops on first error
set -e

# Build the jsBrowserDistribution using Gradle
./gradlew jsApp:jsBrowserDistribution

# Remove the 'dist' directory if it exists and then create a new 'dist' directory
rm -rf dist && mkdir dist

# Copy the contents of 'build/distributions/' to the 'dist' directory
cp -r jsApp/build/distributions/* dist/

echo "Distribution script for jsApp executed successfully!"