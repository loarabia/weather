# Day 1 - Project Start
Things I'm asking myself
## How should I store data?
The application is simple requiring a few key pieces of data and there are quite a few ways I could store it.

### User Data 
First, I need a user with a prefrence list.

```
<USER> = 
{ 
    userid: <int:id>,
    favorites: [ <CITIES> ]
}

<CITIES> = 
    <CITY>  
    <CITY> , <CITIES>
```

Second, I need a telemetry store to keep track of certain vitals.

For this solution, I will use Storage Blobs to store the data since its so simple. I've also read recently from Troy Hunt of Have I been Pwned that he's found blobs to be faster even than Storage Tables. Throwing a DB or Cosmos at this task seems like overkill. I will however endeavour to architect the REST endpoints such that a DB could replace this simple storage at a later time.

 Blobs appears to be an order of magnitude cheaper per GB than Tables and I don't think I need the advanced features. 
 
 Cosmos appears to bill by the hour and I have no desire to try and figure out what that is. It could potentially be cheaper though.

 Similarly, SQL services appear to be overkill and the pricing looks difficult to figure out. 

 ### Statistics and Logs
 The other data I need will be logs and stats about the usage. From looking at documentation under DevOps and Management/Governance I think Monitor is the likely service. 
 
 I looked under Analytics but didn't see anything interesting. Same thing with Integration.

 I also considered trying to go my own way but I'm going to try and use something first because this seems like it could be hard.

## Should I store the access token on the backend?
Reading over Auth0 content. initial thought is no don't store tokens on backend unless in a KeyVault Secrets Store. Trying to learn about best practices. How do I handle refresh?

## For dealing with tokens, I need to send potentially sensitive info over the wire. Need HTTPS? How do I handle that?

Plan right now is to mock up two pieces.
1. Simple structure of the app
2. Login and using a token insecurely to have a user authenticated
3. Actually storing data

Will leave logging and identity security for later efforts.

# Day 2 - more research on security

## 0730-0830

Trying to research what to do with a Facebook Access Token and refresh.

Refresh seems like i'll start getting HTTP 190s at some point and will have to ask for reauth. This might be around 60 days. Need to learn more. Keep seeing and hearing about JWTs and started thinking that KeyVault Secrets would be the right place to be. None of the keyvault docs discuss JWTs and I haven't found any real info on common auth flows either in the docs yet. 

There appear to be literally no code samples on KeyVault.

There are a lot of how to's but they don't appear useful or on the topics im thinking about (best practices for securing the info like tokens related to logins and refreshing it).

Maybe im overcomplicating this. I don't need API access but instead just a simple identity based on user access with no other permissions granted.

🔲 SIDENOTE: Need to handle my own App's secrets from FB.

What FB gives me:

```
{
    status: 'connected',
    authResponse: {
        accessToken: '{access-token}',
        expiresIn:'{unix-timestamp}',
        reauthorize_required_in:'{seconds-until-token-expires}',
        signedRequest:'{signed-parameter}',
        userID:'{user-id}'
    }
}
```

userID becomes likely a container name in my current model.

The other fields I want to put into a blob called `ACCESS`

FB JS SDK may by default put acccess token as a secure cookie which might be okay for me meaning i only need the userid. Need to read more. Oh god even FB only lightly documents this.

Assuming the token is stored in a cookie with an expiration, then on the flask side my current POR is 

I'm probably going too deep down the rabbit hole bcause I'm designing this right now so that if someone signs in once on one browser and then signs in with another browser it will work.

Flask-Social appears to make this easy but there is a lot of magic. Would still like to know what's going on inside. 
https://pythonhosted.org/Flask-Social/ 