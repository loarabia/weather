using System;
using Facebook.LoginKit;
using Weather.Controls;
using Weather.iOS.Renderers;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;

[assembly: ExportRenderer(typeof(FacebookLoginButton), typeof(FacebookLoginButtonRenderer))]
namespace Weather.iOS.Renderers
{
    public class FacebookLoginButtonRenderer: ViewRenderer
    {
        bool disposed;

        protected override void OnElementChanged(ElementChangedEventArgs<View> e)
        {
            base.OnElementChanged(e);
            if (Control == null && e.NewElement != null)
            {
                var fbLoginBtnView = e.NewElement as FacebookLoginButton;
                var fbLoginBtnCtrl = new LoginButton
                {
                    Permissions = fbLoginBtnView.Permissions
                };
                fbLoginBtnCtrl.Completed += AuthCompleted;
                SetNativeControl(fbLoginBtnCtrl);
            }
        }

        void AuthCompleted(object sender, LoginButtonCompletedEventArgs args)
        {
            var view = this.Element as FacebookLoginButton;
            if (args.Error != null)
            {
                view.OnError?.Execute(args.Error.ToString());
            }
            else if (args.Result.IsCancelled)
            {
                view.OnCancel?.Execute(null);
            }
            else
            {
                view.OnSuccess?.Execute(args.Result.Token.TokenString);
            }
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing && !this.disposed)
            {
                if (this.Control != null)
                {
                    (this.Control as LoginButton).Completed -= AuthCompleted;
                    this.Control.Dispose();
                }
                this.disposed = true;
            }
            base.Dispose(disposing);
        }
    }
}
