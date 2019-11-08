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

            // Set up the delegate for changing the view based on login/logout
            loginService.AccessTokenChanged = delegate (string oldToken, string newToken)
            {
                if (oldToken != null && newToken == null)
                {
                    // We logged out
                    MainPage = new LoginPage();
                }
                else if (oldToken == null && newToken != null)
                {
                    // We logged in
                    MainPage = new MainPage();
                }
            };

            if (loginService.AccessToken != null)
            {
                MainPage = new MainPage();
            }
            else
            {
                MainPage = new LoginPage();
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
