ALTER TABLE accidents ADD COLUMN type_id int REFERENCES types(id) NOT NULL;
