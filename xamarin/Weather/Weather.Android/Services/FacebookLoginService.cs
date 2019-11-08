using System;
using Weather.Services;
using Xamarin.Facebook;
using Xamarin.Facebook.Login;

namespace Weather.Droid.Services
{
    public class FacebookLoginService : IFacebookLoginService, IDisposable
    {
        readonly MyAccessTokenTracker myAccessTokenTracker;
        public Action<string, string> AccessTokenChanged { get; set; }

        public FacebookLoginService()
        {
            myAccessTokenTracker = new MyAccessTokenTracker(this);
            myAccessTokenTracker.StartTracking();
        }

        public string AccessToken => Xamarin.Facebook.AccessToken.CurrentAccessToken?.Token;

        public void Logout()
        {
            LoginManager.Instance.LogOut();
        }

        public void Dispose()
        {
            myAccessTokenTracker.StopTracking();
        }
    }

    class MyAccessTokenTracker : AccessTokenTracker
    {
        readonly IFacebookLoginService facebookLoginService;

        public MyAccessTokenTracker(FacebookLoginService facebookLoginService)
        {
            this.facebookLoginService = facebookLoginService;
        }

        protected override void OnCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
            facebookLoginService.AccessTokenChanged?.Invoke(oldAccessToken?.Token, currentAccessToken?.Token);
        }
    }
}
