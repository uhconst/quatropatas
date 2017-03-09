ALTER TABLE pessoa
ALTER COLUMN nascimento type date
USING to_date(nascimento, 'DD/MM/YYYY');