package actions;

import models.User;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class BasicAuthAction extends Action.Simple {

    private static final String AUTHORIZATION = "authorization";
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String REALM = "Basic realm=\"Your Realm Here\"";

    //(SimpleResult)

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {

        String authHeader = context.request().getHeader(AUTHORIZATION);
        if (authHeader == null) {
            context.response().setHeader(WWW_AUTHENTICATE, REALM);
            return F.Promise.pure(unauthorized());
        }

        String auth = authHeader.substring(6);
        byte[] decodedAuth = new sun.misc.BASE64Decoder().decodeBuffer(auth);
        String[] credString = new String(decodedAuth, "UTF-8").split(":");

        if (credString == null || credString.length != 2) {
            return F.Promise.pure(unauthorized());
        }

        String username = credString[0];
        String password = credString[1];
        User authUser = User.authenticate(username, password);

        return (authUser == null) ? F.Promise.pure(unauthorized()) : delegate.call(context);
    }
}