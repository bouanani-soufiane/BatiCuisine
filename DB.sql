CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE project_status AS ENUM ('PENDING', 'DONE', 'CANCELLED');
create type component_type as ENUM ('MATERIAL','WORKER');

CREATE TABLE client (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        name VARCHAR(100) NOT NULL,
                        address VARCHAR(255),
                        phone VARCHAR(50),
                        is_professional BOOLEAN,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        deleted_at TIMESTAMP
);

CREATE TABLE projects (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          name VARCHAR(100) NOT NULL,
                          surface DOUBLE PRECISION NOT NULL,
                          project_status project_status NOT NULL,
                          total_cost DOUBLE PRECISION,
                          profit_margin DOUBLE PRECISION,
                          tva DOUBLE PRECISION,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          deleted_at TIMESTAMP,
                          client_id UUID,
                          CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE estimate (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          issued_date TIMESTAMP NOT NULL,
                          valid_until_date TIMESTAMP NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          deleted_at TIMESTAMP,
                          project_id UUID,
                          CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE component (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           name VARCHAR(100) NOT NULL,
                           component_type component_type NOT NULL,
                           project_id UUID NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                           deleted_at TIMESTAMP,
                           CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE material (
                          quantity DOUBLE PRECISION NOT NULL,
                          unit_price DOUBLE PRECISION NOT NULL,
                          tva DOUBLE PRECISION NOT NULL,
                          transport_cost DOUBLE PRECISION NOT NULL,
                          coefficient DOUBLE PRECISION NOT NULL
) INHERITS (component);

CREATE TABLE workforce (
                           price_per_hour DOUBLE PRECISION NOT NULL,
                           working_hours DOUBLE PRECISION NOT NULL,
                           productivity_factor DOUBLE PRECISION NOT NULL
) INHERITS (component);



INSERT INTO client (name, address, phone, is_professional)
VALUES ('soufiane', '123 Street', '0739829304', TRUE ) RETURNING id;

INSERT INTO projects (name, surface, project_status, total_cost, profit_margin, tva, client_id)
VALUES ('New Project', 150.0, 'ACTIVE', 10000.0, 20.0, 5.0, 'client_id_value');

INSERT INTO material (name, component_type, project_id, quantity, unit_price, tva, transport_cost, coefficient)
VALUES ('Steel Beams', 'MATERIAL', 'c4ea0a7d-c43f-4725-a908-ec3e92c27b16', 500.0, 200.0, 10.0, 150.0, 1.2);

INSERT INTO workforce (name, component_type, project_id, price_per_hour, working_hours, productivity_factor)
VALUES ('Site Supervisor', 'WORKER', 'c4ea0a7d-c43f-4725-a908-ec3e92c27b16', 50.0, 160.0, 1.5);
