SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(50) NOT NULL ,
  `joinDate` DATETIME NOT NULL ,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `security_token` VARCHAR(100) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `user` (`id` ASC) ;
CREATE UNIQUE INDEX `user_email_index` ON `user` (`email` ASC) ;



-- -----------------------------------------------------
-- Table `project`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `project` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `creationDate` DATETIME NULL ,
  `creator` BIGINT UNSIGNED NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `proj_createdby_fk`
    FOREIGN KEY (`creator` )
    REFERENCES `user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `project` (`id` ASC) ;

CREATE INDEX `proj_createdby_fk_idx` ON `project` (`creator` ASC) ;

-- -----------------------------------------------------
-- Table `milestone`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `milestone` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `project` BIGINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `project_milestone_fk`
    FOREIGN KEY (`project` )
    REFERENCES `project` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_milestone_UNIQUE` ON `milestone` (`id` ASC) ;

CREATE INDEX `project_milestone_fk_idx` ON `milestone` (`project` ASC) ;

-- -----------------------------------------------------
-- Table `label`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `label` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `label` VARCHAR(45) NOT NULL ,
  `color` VARCHAR(7) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `label` (`id` ASC) ;




-- -----------------------------------------------------
-- Table `issue`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issue` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `subject` VARCHAR(100) NOT NULL ,
  `description` VARCHAR(20000) NOT NULL ,
  `reporter` BIGINT UNSIGNED NULL ,
  `assigned` BIGINT UNSIGNED NULL ,
  `createdDate` DATETIME NOT NULL ,
  `milestone` BIGINT UNSIGNED NULL ,
  `status` VARCHAR(1) NOT NULL ,
  `project` BIGINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`ID`) ,
  CONSTRAINT `issue_reporter_fk`
    FOREIGN KEY (`reporter` )
    REFERENCES `user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `issue_assigned_fk`
    FOREIGN KEY (`assigned` )
    REFERENCES `user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `issue_milestone_fk`
    FOREIGN KEY (`milestone` )
    REFERENCES `milestone` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `issue_project_fk`
    FOREIGN KEY (`project` )
    REFERENCES `project` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `ID_UNIQUE` ON `issue` (`ID` ASC) ;

CREATE INDEX `issue_creater_fk_idx` ON `issue` (`reporter` ASC) ;

CREATE INDEX `issue_assigned_fk_idx` ON `issue` (`assigned` ASC) ;

CREATE INDEX `issue_milestone_fk_idx` ON `issue` (`milestone` ASC) ;

CREATE INDEX `issue_project_fk_idx` ON `issue` (`project` ASC) ;


-- -----------------------------------------------------
-- Table `issue_label`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `issue_label` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `issue` BIGINT UNSIGNED NOT NULL ,
  `label` BIGINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `issuelabel_issue_fk`
    FOREIGN KEY (`issue` )
    REFERENCES `issue` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `issuelabel_label_fk`
    FOREIGN KEY (`label` )
    REFERENCES `label` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `issue_label` (`id` ASC) ;

CREATE INDEX `issuelabel_issue_fk_idx` ON `issue_label` (`issue` ASC) ;

CREATE INDEX `issuelabel_label_fk_idx` ON `issue_label` (`label` ASC) ;


-- -----------------------------------------------------
-- Table `comment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `comment` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `text` VARCHAR(5000) NOT NULL ,
  `issue` BIGINT UNSIGNED NOT NULL ,
  `createdDate` DATETIME NOT NULL ,
  `creator` BIGINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `issue_comment_fk`
    FOREIGN KEY (`issue` )
    REFERENCES `issue` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `comment_creater_fk`
    FOREIGN KEY (`creator` )
    REFERENCES `user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `issue_comment_fk_idx` ON `comment` (`issue` ASC) ;

CREATE INDEX `comment_creater_fk_idx` ON `comment` (`creator` ASC) ;

CREATE UNIQUE INDEX `id_comment_UNIQUE` ON `comment` (`id` ASC) ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
