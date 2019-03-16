CREATE TABLE api_keys(
    id uuid NOT NULL PRIMARY KEY,
    client_id text NOT NULL,
    client_secret text NOT NULL
);

CREATE TABLE guilds(
    id uuid NOT NULL PRIMARY KEY,
    guild_name text NOT NULL,
    realm text NOT NULL,
    locale text
);
