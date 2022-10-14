package com.devforce.devForce;

import com.devforce.devForce.model.entity.*;
import com.devforce.devForce.model.enums.ERole;
import com.devforce.devForce.repository.LicenciaRepository;
import com.devforce.devForce.repository.RoleRepository;
import com.devforce.devForce.repository.SolicitudRepository;
import com.devforce.devForce.repository.UsuarioRepository;
import com.devforce.devForce.service.UsuarioService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class UserInitializer implements CommandLineRunner {

    @Value("${sample.data}")
    private Boolean datosDePrueba;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private LicenciaRepository licenciaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

        if (datosDePrueba) {

            Role userRole = new Role(ERole.ROLE_USUARIO);
            Role mentorRole = new Role(ERole.ROLE_MENTOR);
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(userRole);
            roleRepository.save(mentorRole);
            roleRepository.save(adminRole);

            Set<Role> userRoles = new HashSet();
            Role r = roleRepository.findByName(ERole.ROLE_USUARIO).orElse(null);
            userRoles.add(r);

            Set<Role> mentorRoles = new HashSet();
            Role m = roleRepository.findByName(ERole.ROLE_MENTOR).orElse(null);
            mentorRoles.add(r);
            mentorRoles.add(m);

            Set<Role> adminRoles = new HashSet();
            Role a = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
            adminRoles.add(a);


            

            // TODO faltan las relaciones. Actualizar una vez hechas las mismas para generar los objetos correctos.
            log.info("Starting to initialize sample data...");
            Faker faker = new Faker();

            System.out.println("------------ USUARIOS -------------");

            for (int i = 1; i < 11; i++) {
                Usuario user = new Usuario();
                user.setId(i);
                user.setNombre(faker.name().firstName());
                user.setApellido(faker.name().lastName());
                user.setUsername(user.getNombre() + user.getApellido());
                user.setEmail(user.getNombre() + user.getApellido() + "@gire.com");
                user.setPassword(encoder.encode(user.getNombre()+"123"));
                user.setPhone(faker.phoneNumber().cellPhone());
                user.setHasTeams(faker.random().nextBoolean());
                user.setRoles(userRoles);
                System.out.println(user);
                usuarioRepository.save(user);
            }


            //USUARIOS DE PRUEBA
            //TEST USUARIO TIPO USER****************************************************************
            Usuario userUser = new Usuario();
            userUser.setId(11);
            userUser.setNombre("Nicolas");
            userUser.setApellido("Rivas");
            userUser.setUsername(userUser.getNombre() + userUser.getApellido());
            userUser.setEmail((userUser.getNombre() + "." + userUser.getApellido() + "@gire.com").toLowerCase());
            userUser.setPassword(encoder.encode(userUser.getNombre()+"123"));
            userUser.setPhone("123456789");
            userUser.setHasTeams(true);
            userUser.setRoles(userRoles);
            usuarioRepository.save(userUser);
            System.out.println(userUser.toString());
            //****************************************************************************************

            //TEST USUARIO TIPO MENTOR****************************************************************
            Usuario userMentor = new Usuario();
            userMentor.setId(12);
            userMentor.setNombre("Javier");
            userMentor.setApellido("Ottina");
            userMentor.setUsername(userMentor.getNombre() + userMentor.getApellido());
            userMentor.setEmail((userMentor.getNombre() + "." + userMentor.getApellido() + "@gire.com").toLowerCase());
            userMentor.setPassword(encoder.encode(userMentor.getNombre()+"123"));
            userMentor.setPhone("123456789");
            userMentor.setHasTeams(true);
            userMentor.setMentorArea("BACKEND");
            userMentor.setRoles(mentorRoles);
            usuarioRepository.save(userMentor);
            System.out.println(userMentor.toString());

            //TEST USUARIO TIPO ADMIN****************************************************************
            Usuario userAdmin = new Usuario();
            userAdmin.setId(13);
            userAdmin.setNombre("Adrian");
            userAdmin.setApellido("Pierro");
            userAdmin.setUsername(userAdmin.getNombre() + userAdmin.getApellido());
            userAdmin.setEmail((userAdmin.getNombre() + "." + userAdmin.getApellido() + "@gire.com").toLowerCase());
            userAdmin.setPassword(encoder.encode(userAdmin.getNombre()+"123"));
            userAdmin.setPhone("123456789");
            userAdmin.setHasTeams(true);
            userAdmin.setRoles(adminRoles);
            usuarioRepository.save(userAdmin);
            System.out.println(userAdmin.toString());
            //****************************************************************************************

            System.out.println("------------ SOLICITUDES -------------");

            for (int i = 1; i < 11; i++) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId(i);
                solicitud.setTipo("UDEMY");
                solicitud.setDescripcion(faker.chuckNorris().fact());
                solicitud.setEstado("PENDIENTE-MENTOR");
                solicitud.setArea("BACKEND");
                solicitud.setTiempoSolicitado(i);
                solicitud.setUsuario(usuarioRepository.findById(1l));
                System.out.println(solicitud);
                solicitudRepository.save(solicitud);
            }

            //SOLICITUDES DE PRUEBA
            Solicitud solicitud1 = new Solicitud();
            solicitud1.setId(11);
            solicitud1.setTipo("UDEMY");
            solicitud1.setDescripcion("HOLA");
            solicitud1.setArea("BACKEND");
            solicitud1.setEstado("PENDIENTE-MENTOR");
            solicitud1.setUsuario(userUser);
            solicitudRepository.save(solicitud1);
            System.out.println(solicitud1.toString());

            Solicitud solicitud2 = new Solicitud();
            solicitud2.setId(12);
            solicitud2.setTipo("UDEMY");
            solicitud2.setDescripcion("HOLA");
            solicitud2.setArea("BACKEND");
            solicitud2.setEstado("PENDIENTE-MENTOR");
            solicitud2.setUsuario(userMentor);
            solicitudRepository.save(solicitud2);
            System.out.println(solicitud2.toString());


            System.out.println("------------ LICENCIAS -------------");
            for (int i = 1; i < 11; i++) {
                Licencia licencia = new Licencia();
                licencia.setId(i);
                licencia.setSerie(faker.bothify("????##?###???###"));
                licencia.setEstado("DISPONIBLE");
                licencia.setPlataforma("UDEMY");
                licencia.setSolicitudes(new ArrayList<>());
                System.out.println(licencia);
                licenciaRepository.save(licencia);
            }
            //LICENCIA DE PRUEBA
            Licencia licenciaPrueba= licenciaRepository.findById(1L);
            System.out.println("licenciaPrueba = " + licenciaPrueba);
            log.info("Finished with data initialization");


            Licencia licenciaNueva = new Licencia();
            licenciaNueva.setId(20);
            licenciaNueva.setSerie(faker.bothify("????##?###???###"));
            licenciaNueva.setEstado("DISPONIBLE");
            licenciaNueva.setPlataforma("OTRA PLATAFORMA");
            licenciaNueva.setSolicitudes(new ArrayList<>());
            System.out.println(licenciaNueva);
            licenciaRepository.save(licenciaNueva);



            Usuario usuarioRubio = new Usuario();
            usuarioRubio.setId(20);
            usuarioRubio.setNombre("Agustin");
            usuarioRubio.setApellido("Rubio");
            usuarioRubio.setUsername("AgustinRubio");
            usuarioRubio.setEmail((usuarioRubio.getNombre()+"."+usuarioRubio.getApellido()+"@gire.com").toLowerCase());
            usuarioRubio.setPassword(encoder.encode(usuarioRubio.getNombre()+"123"));
            usuarioRubio.setPhone("123456789");
            usuarioRubio.setHasTeams(false);
            usuarioRubio.setRoles(userRoles);
            System.out.println(usuarioRubio.toString());
            usuarioRepository.save(usuarioRubio);

            Usuario userUserValenBara = new Usuario();
            userUserValenBara.setId(21);
            userUserValenBara.setNombre("Valentin");
            userUserValenBara.setApellido("Barallobre");
            userUserValenBara.setUsername("ValentinBarallobre");
            userUserValenBara.setEmail((userUserValenBara.getNombre()+"."+userUserValenBara.getApellido()+"@gire.com").toLowerCase());
            userUserValenBara.setPassword(encoder.encode(userUserValenBara.getNombre()+"123"));
            userUserValenBara.setPhone("123356789");
            userUserValenBara.setHasTeams(true);
            userUserValenBara.setRoles(userRoles);
            System.out.println(userUserValenBara.toString());
            usuarioRepository.save(userUserValenBara);

        }
    }
}
