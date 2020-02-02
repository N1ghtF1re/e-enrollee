package men.brakh.enrollment.application.mapping;

import lombok.val;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean("particularModelMapper")
  public ModelMapper particularModelMapper() {
    val modelMapper = new ModelMapper();
    modelMapper
        .getConfiguration()
        .setAmbiguityIgnored(true)
        .setPropertyCondition(Conditions.isNotNull());

    return modelMapper;
  }
}
