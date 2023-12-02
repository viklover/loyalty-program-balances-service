CREATE OR REPLACE FUNCTION update_change_timestamp_balance()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ language 'plpgsql';
