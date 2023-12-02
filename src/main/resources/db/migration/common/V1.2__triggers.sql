CREATE TRIGGER update_change_timestamp_balance BEFORE UPDATE
    ON BALANCE FOR EACH ROW EXECUTE PROCEDURE
    update_change_timestamp_balance();
