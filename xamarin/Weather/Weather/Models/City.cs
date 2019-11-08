using System;
using System.Linq;
using System.Threading.Tasks;
using Xamarin.Essentials;

namespace Weather.Models
{
    public class City
    {
        public string? Locality  { get; private set; }
        public string? Country   { get; private set; }
        public double? Latitude  { get; private set; }
        public double? Longitude { get; private set; }

        public async Task<City> WithLocality(string locality, string country)
        {
            Locality = locality;
            Country = country;
            await GetLocationData($"{Locality}, {Country}");
            return this;
        }

        public async Task<City> WithLocation(double latitude, double longitude)
        {
            Latitude = latitude;
            Longitude = longitude;
            await GetLocalityData(latitude, longitude);
            return this;
        }

        // Get the lat/long based on the locality/country
        private async Task GetLocationData(string address)
        {
            try
            {
                var locations = await Geocoding.GetLocationsAsync(address);
                var location = locations?.FirstOrDefault();
                if (location != null)
                {
                    Latitude = location.Latitude;
                    Longitude = location.Longitude;
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Geocoding Failed: {ex.Message}");
                Latitude = null;
                Longitude = null;
            }
        }

        // Get the locality/country based on the lat/long
        private async Task GetLocalityData(double latitude, double longitude)
        {
            try
            {
                var placemarks = await Geocoding.GetPlacemarksAsync(new Location(latitude, longitude));
                var place = placemarks?.FirstOrDefault();
                if (place != null)
                {
                    Locality = place.Locality;
                    Country = place.CountryName;
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine($"Geocoding Failed: {ex.Message}");
                Locality = null;
                Country = null;
            }
        }
    }
}
