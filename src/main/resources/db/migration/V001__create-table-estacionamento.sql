create table estacionamento(
	id bigint not null auto_increment,
    cnpj  varchar(20) not null,
    endereco varchar(255) not null,
    telefone varchar(20) not null,
    vagas_para_motos bigint not null,
	vagas_para_carros bigint not null,
    
    primary key(id)
);