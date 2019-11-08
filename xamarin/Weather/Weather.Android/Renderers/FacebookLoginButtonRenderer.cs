using System;
using Android.Content;
using Java.Lang;
using Weather.Controls;
using Weather.Droid.Renderers;
using Xamarin.Facebook;
using Xamarin.Facebook.Login;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(FacebookLoginButton), typeof(FacebookLoginButtonRenderer))]
namespace Weather.Droid.Renderers
{
    public class FacebookLoginButtonRenderer: ViewRenderer
    {
        Context ctx;
        bool disposed;

        public FacebookLoginButtonRenderer(Context ctx): base(ctx)
        {
            this.ctx = ctx;
        }

        protected override void OnElementChanged(ElementChangedEventArgs<View> e)
        {
            if (Control == null && e.NewElement != null)
            {
                var fbLoginBtnView = e.NewElement as FacebookLoginButton;
                var fbLoginBtnCtrl = new Xamarin.Facebook.Login.Widget.LoginButton(ctx)
                {
                    LoginBehavior = LoginBehavior.NativeWithFallback
                };

                fbLoginBtnCtrl.SetPermissions(fbLoginBtnView.Permissions);
                fbLoginBtnCtrl.RegisterCallback(MainActivity.CallbackManager,
                    new MyFacebookCallback(this.Element as FacebookLoginButton));
                SetNativeControl(fbLoginBtnCtrl);
            }
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing && !this.disposed)
            {
                if (this.Control != null)
                {
                    (this.Control as Xamarin.Facebook.Login.Widget.LoginButton)
                        .UnregisterCallback(MainActivity.CallbackManager);
                    this.Control.Dispose();
                }
                this.disposed = true;
            }
            base.Dispose(disposing);
        }

        class MyFacebookCallback : Java.Lang.Object, IFacebookCallback
        {
            FacebookLoginButton view;

            public MyFacebookCallback(FacebookLoginButton view)
            {
                this.view = view;
            }

            public void OnCancel()
            {
                view.OnCancel?.Execute(null);
            }

            public void OnError(FacebookException error)
            {
                view.OnError?.Execute(error.Message);
            }

            public void OnSuccess(Java.Lang.Object result)
            {
                view.OnSuccess?.Execute(((LoginResult)result).AccessToken.Token);
            }
        }
    }
}
