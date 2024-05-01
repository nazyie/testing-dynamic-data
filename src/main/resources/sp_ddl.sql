-- store procedure for the table
-- db type: mysql

-- get list of data
DELIMITER //

CREATE PROCEDURE GetUserList()
BEGIN
    SELECT email, username FROM `user`;
END//

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetUserListWithPagination(IN page INT, IN size INT)
BEGIN
    DECLARE startRow INT DEFAULT page;
    DECLARE numRows INT DEFAULT size;

    SELECT email, username FROM `user`
    LIMIT numRows
    OFFSET startRow;
END//

DELIMITER ;



-- get the list of data using the searching

DELIMITER //

CREATE PROCEDURE GetUserListByUsernamePrefix(IN usernamePrefix VARCHAR(255))
BEGIN
    SELECT email, username FROM `user`
    WHERE username LIKE CONCAT(usernamePrefix, '%');
END//

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetUserListByUsernamePrefixWithPagination(IN usernamePrefix VARCHAR(255), IN page INT, IN size INT)
BEGIN
    DECLARE startRow INT DEFAULT page * size;
    DECLARE numRows INT DEFAULT size;

    SELECT email, username FROM `user`
    WHERE username LIKE CONCAT(usernamePrefix, '%')
    LIMIT numRows
    OFFSET startRow;
END//

DELIMITER ;

