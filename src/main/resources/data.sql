INSERT INTO tb_user (username, email, password, enabled) VALUES ('Alex Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true);
INSERT INTO tb_user (username, email, password, enabled) VALUES ('Maria Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true);
INSERT INTO tb_user (username, email, password, enabled) VALUES ('Bob Black', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', true);

INSERT INTO tb_role (authority) VALUES ('ROLE_VISITOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);

INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lavar o banheiro', null, false, 1, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Limpar o quarto', null, false, 1, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Trocar roupas de cama', null, false, 1, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Escovar os gatos', null, false, 1, 'medium');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Beber 3L de água', null, false, 1, 'low');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Fazer exercício de Java', null, false, 1, 'low');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Academia', null, false, 1, 'low');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Praticar violão', null, false, 1, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Estudar cursos', null, false, 1, 'medium');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 1', null, false, 2, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 2', null, false, 2, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 3', null, false, 2, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 4', null, false, 2, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 5', null, false, 2, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 6', null, false, 3, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 7', null, false, 3, 'high');
INSERT INTO tb_todo (title, created_at, done, user_id, priority) VALUES ('Lorem ipsum 8', null, false, 3, 'high');

INSERT INTO tb_card (title, description, created_at, user_id) VALUES ('Dia 07/10', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque elementum scelerisque sem vitae varius. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed ligula tellus, tincidunt non mollis vel, viverra quis felis. Donec at mattis ex. Etiam nec porta orci. Sed dignissim orci lectus, at scelerisque nulla ornare quis. Aliquam at odio ut lacus finibus tristique. Phasellus lobortis arcu quis nisi vestibulum sagittis finibus commodo orci. Quisque cursus feugiat lorem quis efficitur. Phasellus lacus mauris, lacinia tincidunt molestie a, ultricies ac lacus. Ut et mauris sit amet nisl efficitur iaculis pharetra sit amet enim. Nulla facilisi. Aliquam ullamcorper metus lacus, nec euismod quam consequat id.', null, 1);
INSERT INTO tb_card (title, description, created_at, user_id) VALUES ('Dia 08/10', 'Ut pulvinar, mauris fermentum sagittis viverra, urna arcu pretium neque, non luctus urna leo sed lacus. Proin nulla sapien, fermentum et dui sed, gravida iaculis diam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur eleifend nec leo sit amet dapibus. Sed tellus urna, iaculis ultricies mollis et, placerat eu risus. Donec fringilla tempus mauris, ac euismod lorem maximus non. Sed ipsum odio, tempus tincidunt porta non, tempus nec elit. Nam tincidunt finibus ipsum. Suspendisse bibendum metus augue, in varius purus pretium quis. Vestibulum ornare ultrices risus et pretium. Nam bibendum leo at turpis viverra laoreet. Suspendisse hendrerit, tellus eget vestibulum hendrerit, metus dolor ultricies risus, sed placerat tellus dui ac velit. Cras eget nunc aliquet, tincidunt justo at, hendrerit massa. Nullam ut tellus quis lorem auctor interdum in et leo. Aenean vehicula fermentum tellus quis feugiat. Fusce venenatis lacinia scelerisque.', null, 1);
INSERT INTO tb_card (title, description, created_at, user_id) VALUES ('Dia 09/10', 'Ut erat velit, varius ac mauris ac, pretium convallis ex. Vivamus finibus massa nisl, consequat cursus arcu tincidunt id. Nam quis magna vitae est pretium congue vel a elit. Ut aliquam convallis nibh eu suscipit. Sed fermentum fermentum justo vitae faucibus. Vivamus sed suscipit lacus, non tincidunt velit. Nullam fermentum felis at mi tempus, interdum aliquam felis suscipit. Praesent erat libero, consequat id vulputate interdum, congue in nunc. Quisque vitae lobortis eros. Mauris eget faucibus ante. Vivamus consequat ut quam sed semper. Vestibulum eget urna mi. Donec id sapien dolor. Nunc nec elit ac mauris maximus cursus fermentum nec mauris. Aenean aliquet at ipsum vel cursus.', null, 1);
INSERT INTO tb_card (title, description, created_at, user_id) VALUES ('Dia 10/10', 'Nullam sapien nunc, tincidunt non pretium in, condimentum a nulla. Curabitur justo elit, elementum eget nibh ac, fermentum volutpat enim. Phasellus ornare ex eu turpis convallis, sed pharetra neque suscipit. Praesent eget lacus vehicula, aliquet risus vel, ultricies dui. Nulla facilisi. Aenean sed purus bibendum, accumsan nisi quis, finibus velit. Fusce non consectetur justo, at condimentum tortor. Phasellus pretium aliquam luctus.', null, 1);

INSERT INTO tb_streak (title, count, total, disabled, total_per_label, user_id, created_at, label, last) VALUES ('Sem álcool', 2, 30, false, 30, 1, null, 'dias', '2022-11-01T17:32:34.520054200Z');
INSERT INTO tb_streak (title, count, total, disabled, total_per_label, user_id, created_at, label, last) VALUES ('Estudar', 5, 60, false, 420, 1, null, 'semanas', '2022-11-01T17:32:34.520054200Z');