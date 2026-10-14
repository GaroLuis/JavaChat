CREATE TABLE chat.user_contacts (
    user_id UUID NOT NULL,
    contact_id UUID NOT NULL,
    CONSTRAINT pk_user_contacts PRIMARY KEY (user_id, contact_id),
    CONSTRAINT fk_user_contacts_user FOREIGN KEY (user_id) REFERENCES chat.users(id),
    CONSTRAINT fk_user_contacts_contact FOREIGN KEY (contact_id) REFERENCES chat.users(id),
    CONSTRAINT chk_user_contacts_not_self CHECK (user_id <> contact_id)
);
