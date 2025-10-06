import requests

def get_coordinates(city):
    url = "https://geocoding-api.open-meteo.com/v1/search"
    params = {"name": city, "count": 1}
    response = requests.get(url, params=params)
    data = response.json()

    if "results" in data and len(data["results"]) > 0:
        result = data["results"][0]
        return result["latitude"], result["longitude"], result["name"], result.get("country", "")
    else:
        return None, None, None, None

def get_weather(city):
    lat, lon, city_name, country = get_coordinates(city)
    if lat is None:
        print(f"City '{city}' not found.")
        return

    url = "https://api.open-meteo.com/v1/forecast"
    params = {
        "latitude": lat,
        "longitude": lon,
        "current_weather": True
    }
    response = requests.get(url, params=params)
    data = response.json()

    if "current_weather" in data:
        weather = data["current_weather"]
        temp = weather["temperature"]
        wind_speed = weather["windspeed"]
        weather_code = weather["weathercode"]

        weather_descriptions = {
            0: ("Clear", "Clear sky"),
            1: ("Mainly Clear", "Mainly clear"),
            2: ("Partly Cloudy", "Partly cloudy"),
            3: ("Overcast", "Overcast"),
            45: ("Fog", "Foggy"),
            48: ("Depositing Rime Fog", "Rime fog"),
            51: ("Light Drizzle", "Light drizzle"),
            53: ("Moderate Drizzle", "Moderate drizzle"),
            55: ("Dense Drizzle", "Dense drizzle"),
            61: ("Slight Rain", "Slight rain"),
            63: ("Moderate Rain", "Moderate rain"),
            65: ("Heavy Rain", "Heavy rain"),
            71: ("Slight Snow", "Slight snow"),
            73: ("Moderate Snow", "Moderate snow"),
            75: ("Heavy Snow", "Heavy snow"),
            77: ("Snow Grains", "Snow grains"),
            80: ("Slight Rain Showers", "Slight rain showers"),
            81: ("Moderate Rain Showers", "Moderate rain showers"),
            82: ("Violent Rain Showers", "Violent rain showers"),
            85: ("Slight Snow Showers", "Slight snow showers"),
            86: ("Heavy Snow Showers", "Heavy snow showers"),
            95: ("Thunderstorm", "Thunderstorm"),
            96: ("Thunderstorm with Hail", "Thunderstorm with slight hail"),
            99: ("Thunderstorm with Heavy Hail", "Thunderstorm with heavy hail"),
        }

        main, desc = weather_descriptions.get(weather_code, ("Unknown", "No description"))

        print(f"{city_name}'s temperature: {temp}°C")
        print(f"Wind speed: {wind_speed} m/s")
        print(f"Description: {desc}")
        print(f"Weather: {main}")
    else:
        print("Weather data not available.")

if __name__ == "__main__":
    city = input("Enter the city: ")
    get_weather(city)
