ALTER TABLE project ADD COLUMN displayName VARCHAR(50);
CREATE UNIQUE INDEX `project_name_index` ON `project` (`name` ASC) ;