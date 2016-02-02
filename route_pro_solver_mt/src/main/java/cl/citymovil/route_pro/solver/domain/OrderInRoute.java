package cl.citymovil.route_pro.solver.domain;


//@Entity
public class OrderInRoute {

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "customerInRouteId")
	protected ScheduledCustomer customerInRoute;

	private float demand;

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long orderInRouteId;

	public OrderInRoute() {
	}

	public OrderInRoute(Order order) {
		this.demand = order.getDemand();
	}

	public ScheduledCustomer getCustomerInRoute() {
		return customerInRoute;
	}

	public float getDemand() {
		return demand;
	}

	public long getOrderInRouteId() {
		return orderInRouteId;
	}

	public void setCustomerInRoute(ScheduledCustomer customerInRoute) {
		this.customerInRoute = customerInRoute;
	}

	public void setDemand(float demand) {
		this.demand = demand;
	}

	public void setOrderInRouteId(long orderInRouteId) {
		this.orderInRouteId = orderInRouteId;
	}

}
