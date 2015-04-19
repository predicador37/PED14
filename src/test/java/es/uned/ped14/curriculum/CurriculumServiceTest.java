package es.uned.ped14.curriculum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CurriculumServiceTest {

	@InjectMocks
	private CurriculumService curriculumService = new CurriculumService();

	@Mock
	private CurriculumRepository curriculumRepositoryMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldInitializeWithOneDemoCurriculum() {
		// act
		curriculumService.initialize();
		// assert
		verify(curriculumRepositoryMock, times(1)).save(any(Curriculum.class));
	}


	@Test
	public void shouldReturnCurriculumById() throws CurriculumNotFoundException {
		// arrange
		Curriculum demoCurriculum = new Curriculum("Miguel", "Expósito Martín", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		when(curriculumRepositoryMock.findOne(1L)).thenReturn(demoCurriculum);

		// act
		Curriculum curriculum = curriculumService.find(1L);

		// assert
		assertThat(demoCurriculum.getNombre()).isEqualTo(curriculum.getNombre());
		assertThat(demoCurriculum.getCiudad()).isEqualTo(curriculum.getCiudad());
	}
	
	@Test
	public void shouldReturnCurriculumByPaisAndCiudad() throws CurriculumNotFoundException {
		// arrange
		Curriculum demoCurriculum1 = new Curriculum("Miguel", "Expósito", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum2 = new Curriculum("Héctor", "Garnacho", "España", "Valladolid", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		Curriculum demoCurriculum3 = new Curriculum("Marcos", "Azorí", "España", "Madrid", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		List<Curriculum> demoCurriculos = new ArrayList<Curriculum>();
		demoCurriculos.add(demoCurriculum1);
		
		when(curriculumRepositoryMock.findByPaisAndCiudad("España", "Santander")).thenReturn(demoCurriculos);

		// act
		List<Curriculum> curriculos = curriculumService.findByPaisAndCiudad("España", "Santander");
     
		// assert
		for (Curriculum c : curriculos) {
			assertThat(demoCurriculum1.getNombre()).isEqualTo(c.getNombre());
			assertThat(demoCurriculum1.getCiudad()).isEqualTo(c.getCiudad());
		}
	}

	
}
