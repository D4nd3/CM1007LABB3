import express, {Request,Response,NextFunction} from 'express';
import bodyParser from 'body-parser';
import jwt from 'jsonwebtoken';
import jwksRsa from 'jwks-rsa';
import uploadRoutes from './routes/upload';
import editRoutes from './routes/edit';

const app = express();
const PORT = process.env.PORT;
const keycloakIssuer = `http://keycloak/realms/${process.env.KEYCLOAK_REALM}`;

const jwtCheck: express.RequestHandler = (req, res, next) => {
  const token = req.headers.authorization?.split(' ')[1];

  if(!token){
    res.status(401).send('Missing token');
    return; 
  }

  const client = jwksRsa({
    jwksUri: `${process.env.KEYCLOAK_URL}/realms/${process.env.KEYCLOAK_REALM}/protocol/openid-connect/certs`
  });

  const getKey = (header: any, callback: any) => {
    client.getSigningKey(header.kid, (err, key) => {
      if (err || !key) {
        console.error('Failed to get signing key:', err);
        return callback(new Error('Failed to get signing key'));
      }
      const signingKey = key.getPublicKey();
      callback(null, signingKey);
    });
  };

  console.log('Token received:', token);
  console.log('Issuer:', keycloakIssuer);

  jwt.verify(token, getKey, {
    algorithms: ['RS256'],
    issuer: keycloakIssuer
  }, (err, decoded) => {
    if (err) {
      console.error('JWT error:', err.message);
      res.status(401).send('Invalid token');
      return; 
    }

    req.user = decoded;
    next();
  });
};

app.use(bodyParser.json());

app.use(jwtCheck);


app.use('/uploads', express.static('uploads'));

app.use('/api/upload', uploadRoutes);
app.use('/api/edit', editRoutes);

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});