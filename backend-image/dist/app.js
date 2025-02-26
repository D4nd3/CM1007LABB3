"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
const jwks_rsa_1 = __importDefault(require("jwks-rsa"));
const upload_1 = __importDefault(require("./routes/upload"));
const edit_1 = __importDefault(require("./routes/edit"));
const app = (0, express_1.default)();
const PORT = process.env.PORT;
const keycloakIssuer = `http://keycloak/realms/${process.env.KEYCLOAK_REALM}`;
const jwtCheck = (req, res, next) => {
    const token = req.headers.authorization?.split(' ')[1];
    if (!token) {
        res.status(401).send('Missing token');
        return;
    }
    const client = (0, jwks_rsa_1.default)({
        jwksUri: `${process.env.KEYCLOAK_URL}/realms/${process.env.KEYCLOAK_REALM}/protocol/openid-connect/certs`
    });
    const getKey = (header, callback) => {
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
    jsonwebtoken_1.default.verify(token, getKey, {
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
app.use(body_parser_1.default.json());
app.use(jwtCheck);
app.use('/uploads', express_1.default.static('uploads'));
app.use('/api/upload', upload_1.default);
app.use('/api/edit', edit_1.default);
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
