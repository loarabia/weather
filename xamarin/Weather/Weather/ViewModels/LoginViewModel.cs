using System.Windows.Input;
using Weather.Views;
using Xamarin.Forms;

namespace Weather.ViewModels
{
    public class LoginViewModel
    {
        public ICommand OnFacebookLoginSuccessCmd { get; }
        public ICommand OnFacebookLoginErrorCmd { get; }
        public ICommand OnFacebookLoginCancelCmd { get; }

        public LoginViewModel()
        {
            // if we successfully login, then switch to the new main page.
            OnFacebookLoginSuccessCmd = new Command<string>(
                (authToken) => Application.Current.MainPage = new MainPage()
            );

            // On error or cancellation, just pop up an alert, then stop.
            OnFacebookLoginErrorCmd = new Command<string>(
                (err) => DisplayAlert("Error", $"Auth failed: {err}")
            );
            OnFacebookLoginCancelCmd = new Command(
                () => DisplayAlert("Cancel", "Auth cancelled by user")
            );
        }

        void DisplayAlert(string title, string msg) =>
            (Application.Current as App).MainPage.DisplayAlert(title, msg, "OK");
    }
}
