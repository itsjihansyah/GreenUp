#include <ESP8266WiFi.h>
#include <DHT.h>
#include <Firebase_ESP_Client.h>

const char* ssid = "Jey";
const char* password = "..."; 

#define FIREBASE_HOST "..." 
#define FIREBASE_AUTH "..."

#define SOIL_MOISTURE_PIN A0
#define DHT_PIN D2
#define DHT_TYPE DHT11

const String firebasePath = "/Iot/-OD6dujd6CdaWRoxq0j7";

FirebaseData firebaseData;
FirebaseConfig config;
FirebaseAuth auth;
DHT dht(DHT_PIN, DHT_TYPE);

void connectToWiFi() {
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWi-Fi connected!");
}

void initializeFirebase() {
  config.database_url = FIREBASE_HOST;
  config.signer.tokens.legacy_token = FIREBASE_AUTH;
  config.timeout.serverResponse = 10 * 1000;
  Firebase.reconnectWiFi(true);
  Firebase.begin(&config, NULL);

  if (Firebase.ready()) {
    Serial.println("Firebase initialized successfully!");
  } else {
    Serial.println("Failed to initialize Firebase!");
  }
}

void readSensors(int &soilMoisture, float &temperature, float &humidity, float &lightIntensity) {
  soilMoisture = map(analogRead(SOIL_MOISTURE_PIN), 400, 1023, 100, 0);
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  lightIntensity = analogRead(A0) * 0.0976;
}

float calculateHealth(float soilMoisture, float temperature, float humidity, float lightIntensity) {
    float soilScore = (soilMoisture >= 30 && soilMoisture <= 70) 
        ? 100 
        : max(0.0f, 100.0f - abs(50.0f - soilMoisture) * 4.0f);

    float tempScore = (temperature >= 20 && temperature <= 30) 
        ? 100 
        : max(0.0f, 100.0f - abs(25.0f - temperature) * 10.0f);

    float humidityScore = (humidity >= 40 && humidity <= 60) 
        ? 100 
        : max(0.0f, 100.0f - abs(50.0f - humidity) * 2.0f);

    float lightScore = (lightIntensity >= 500 && lightIntensity <= 1000) 
        ? 100 
        : max(0.0f, 100.0f - abs(750.0f - lightIntensity) * 0.1f);

    return (soilScore * 0.4 + tempScore * 0.3 + humidityScore * 0.2 + lightScore * 0.1);
}

void setup() {
    Serial.begin(9600);
    dht.begin();
}

void loop() {
    float soilMoisture = map(analogRead(SOIL_MOISTURE_PIN), 400, 1023, 100, 0);
    float temperature = dht.readTemperature();
    float humidity = dht.readHumidity();
    float lightIntensity = analogRead(SOIL_MOISTURE_PIN) * 0.0976;

    float health = calculateHealth(soilMoisture, temperature, humidity, lightIntensity);

    Serial.print("Soil Moisture: ");
    Serial.print(soilMoisture);
    Serial.print("%, Temperature: ");
    Serial.print(temperature);
    Serial.print("°C, Humidity: ");
    Serial.print(humidity);
    Serial.print("%, Light Intensity: ");
    Serial.print(lightIntensity);
    Serial.print(" lx, Health Score: ");
    Serial.println(health);

    delay(1000);
}
