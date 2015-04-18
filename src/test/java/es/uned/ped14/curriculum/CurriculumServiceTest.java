package es.uned.ped14.curriculum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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
	public void shouldReturnCurriculum() throws CurriculumNotFoundException {
		// arrange
		Curriculum demoCurriculum = new Curriculum("Miguel", "Expósito Martín", "España", "Santander", "htp://localhost/imagen.png", "http://localhost/archivo.pdf");
		when(curriculumRepositoryMock.findOne(1L)).thenReturn(demoCurriculum);

		// act
		Curriculum curriculum = curriculumService.find(1L);

		// assert
		assertThat(demoCurriculum.getNombre()).isEqualTo(curriculum.getNombre());
		assertThat(demoCurriculum.getCiudad()).isEqualTo(curriculum.getCiudad());
	}

	
}
