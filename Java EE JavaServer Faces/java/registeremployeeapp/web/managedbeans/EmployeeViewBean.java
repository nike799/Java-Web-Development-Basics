package registeremployeeapp.web.managedbeans;

import org.modelmapper.ModelMapper;
import registeremployeeapp.domain.models.view.EmployeeViewModel;
import registeremployeeapp.service.EmployeeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class EmployeeViewBean {
    private List<EmployeeViewModel> viewModels;
    private EmployeeService employeeService;
    private ModelMapper modelMapper;

    public EmployeeViewBean() {
        this.viewModels = this.employeeService
                .getAllEmployees()
                .stream()
                .map(employeeServiceModel -> this.modelMapper.map(employeeServiceModel, EmployeeViewModel.class))
                .collect(Collectors.toList());
    }

    @Inject
    public EmployeeViewBean(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.viewModels = this.employeeService
                .getAllEmployees()
                .stream()
                .map(employeeServiceModel -> this.modelMapper.map(employeeServiceModel, EmployeeViewModel.class))
                .collect(Collectors.toList());
    }

    public List<EmployeeViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<EmployeeViewModel> viewModels) {
        this.viewModels = viewModels;
    }

    public BigDecimal getTotalMoneyNeeded() {
        return this.viewModels.stream()
                .map(EmployeeViewModel::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getAverageMoneyNeeded(){
        return this.getTotalMoneyNeeded().divide(BigDecimal.valueOf(this.viewModels.size()), RoundingMode.valueOf(2));
    }

}
