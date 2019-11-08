using Weather.Services;
using Weather.Views;
using Xamarin.Forms;

namespace Weather
{
    public partial class App : Application
    {
        public IFacebookLoginService FacebookLoginService { get; private set; }

        public App(IFacebookLoginService loginService)
        {
            InitializeComponent();
            FacebookLoginService = loginService;
            if (loginService.AccessToken == null)
            {
                MainPage = new LoginPage();
            }
            else
            {
                MainPage = new MainPage();
            }
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
